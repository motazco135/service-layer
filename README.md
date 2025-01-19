System Migration are often complex, particularly when existing services
consumers depends on the old system APIs.

A common challenge is ensuring continuity for consumers while
transitioning to a modern system.

In this article, we’ll explore a migration strategy that introduces a
**service layer** to act as an intermediary between consumers and
systems, ensuring a seamless migration without distribution.

# Migration Overview

Before diving into the implementation let’s define the high level
architecture :

-   **Old System**: The existing system with APIs or services that
    consumers depends on

-   **New System**: The new modernized system where services and data
    will be used

-   **Service Layer**:

    -   Acts as a bridge between the legacy and modern systems.

    -   Transforms the modern system’s response to match the legacy API
        format.

    -   Ensures a seamless transition for existing clients.

# Implementation Example

We’ll start with a simple use case migrating a **customer profile
service**. The goal is to ensure that consumers can continue to retrieve
and update customer profile data during migration.

We will use **Adapter Pattern** in order to decoupling the business
logic from system specific APIs. It allows seamless migration from
legacy system to modern one, enabling backward compatibility while
progressively adopting the new system.

## Step 1: Understanding Migration Challenge

1.  The **old system** expose APIs with specific structure that
    consumers rely on

2.  The **new system** introduces new APIs with improved structure and
    functionality but breaks compatibility with existing consumers.

3.  Consumers must continue using the APIs without knowing about backend
    migration.

## Step 2: Adapter Pattern

-   The Adapter Pattern is used to map the modern system’s response to
    the legacy API format.

-   It allows the service layer to work with both legacy and modern
    systems without affecting consumer facing APIs.

## Step 3: Implementation

1.  **Define The Domain Model**

    The Domain Model is the abstraction layer shared between the service
    layer and adapters.

    -   LegacyCustomerProfileDto : Represent the old system domain class

    -   ModernCustomerProfileDto : Represent the new system domain class
        where you can see the difference in the attribute names like
        "emailAddress","contactNumber","firstName,"lastName" and "id"

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public class LegacyCustomerProfileDto {

                private Long customerId;
                private String fullName;
                private String email;
                private String phoneNumber;

            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public class ModernCustomerProfileDto {

                private Long id;
                private String firstName;
                private String lastName;
                private String emailAddress;
                private String contactNumber;

            }

2.  **Create the Consumer API**

\+ The consumers consume the exposed end points that remains compatible
with the old system while delegating the business logic to the service
layer.

\+

    @RestController
    @RequestMapping("/api/customers")
    @RequiredArgsConstructor
    public class CustomerController {

        private final CustomerService customerService;

        @GetMapping("/{id}")
        public ResponseEntity<LegacyCustomerProfileDto> getCustomer(@PathVariable Long id) {
            LegacyCustomerProfileDto legacyCustomerProfileDto = customerService.getCustomer(id);
            return ResponseEntity.ok(legacyCustomerProfileDto);
        }

        @PostMapping
        public ResponseEntity<LegacyCustomerProfileDto> createCustomer(@RequestBody LegacyCustomerProfileDto legacyCustomerProfileDto) {
            LegacyCustomerProfileDto newCustomerDto = customerService.createCustomer(legacyCustomerProfileDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCustomerDto);
        }

        @PutMapping("/{id}")
        public ResponseEntity<LegacyCustomerProfileDto> updateCustomerProfile(@PathVariable Long id, @RequestBody LegacyCustomerProfileDto legacyCustomerProfileDto) {
            LegacyCustomerProfileDto updatedCustomerProfile = customerService.updateCustomerProfile(id,legacyCustomerProfileDto);
            return ResponseEntity.ok(updatedCustomerProfile);
        }
    }

\+ . **Implement the Service Layer**

\+ The service layer orchestrates the business logic and delegates
transformation tasks to the adapter.

\+

    @Service
    @RequiredArgsConstructor
    public class CustomerService {

        private final CustomerProfileAdapter adapter;
        private final ModernSystemClient modernSystemClient;

        //Get Customer Profile
        public LegacyCustomerProfileDto getCustomer(Long customerId) {
            // Simulate fetching data from the modern system
            ModernCustomerProfileDto modernProfile = modernSystemClient.getModernCustomerProfile(customerId);
            // Map the modern response to legacy format
            return adapter.toLegacy(modernProfile);
        }
    }

\+ . **Implement the Adapter**

\+ maps between the legacy and modern DTOs.

\+

    @Slf4j
    @Component
    public class CustomerProfileAdapter {

        // Map modern to legacy (for get)
        public LegacyCustomerProfileDto toLegacy(ModernCustomerProfileDto modernProfileDto) {
            if(modernProfileDto == null){
                log.error("Transformation failed: modernProfileDto is null");
                throw new IllegalArgumentException("modernProfileDto cannot be null");
            }
            String firstName = modernProfileDto.getFirstName() != null ? modernProfileDto.getFirstName() : "";
            String lastName = modernProfileDto.getLastName() != null ? modernProfileDto.getLastName() : "";

            log.info("Transforming ModernCustomerProfileDto to LegacyCustomerProfileDto: {}", modernProfileDto);
            return  LegacyCustomerProfileDto.builder()
                    .customerId(modernProfileDto.getId())
                    .fullName(firstName + " " + lastName)
                    .email(modernProfileDto.getEmailAddress())
                    .phoneNumber(modernProfileDto.getContactNumber()).build();
        }

        // Map legacy to modern (for create and update)
        public ModernCustomerProfileDto toModern(LegacyCustomerProfileDto legacyProfileDto) {
            if(legacyProfileDto == null){
                log.error("Transformation failed: legacyProfileDto is null");
                throw new IllegalArgumentException("LegacyCustomerProfileDto cannot be null");
            }
            String[] names = legacyProfileDto.getFullName().split(" ",2);
            String firstName = names.length > 0 ? names[0].trim() : "";
            String lastName = names.length > 1 ? names[1].trim() : "";

            log.info("Transforming LegacyCustomerProfileDto to ModernCustomerProfileDto: {}", legacyProfileDto);
            return ModernCustomerProfileDto.builder()
                    .id(legacyProfileDto.getCustomerId())
                    .firstName(firstName)
                    .lastName(lastName)
                    .emailAddress(legacyProfileDto.getEmail())
                    .contactNumber(legacyProfileDto.getPhoneNumber()).build();
        }

    }

\+ . **Implement the Modern System Client**

\+ The modern system client simulates API calls to the new system.

\+

    @Component
    public class ModernSystemClient {

        // Simulate calling the modern system's create API
        public ModernCustomerProfileDto createModernCustomerProfile(ModernCustomerProfileDto modernProfile) {
            // Simulate creating a customer in the modern system
            return modernProfile; // In a real scenario, this would call the modern system's API
        }

        // Simulate calling the modern system's update API
        public ModernCustomerProfileDto updateModernCustomerProfile(Long customerId, ModernCustomerProfileDto modernProfile) {
            // Simulate updating a customer in the modern system
            return modernProfile; // In a real scenario, this would call the modern system's API
        }

        // Simulate calling the modern system's get API
        public ModernCustomerProfileDto getModernCustomerProfile(Long customerId) {
            // Simulate fetching a customer from the modern system
            ModernCustomerProfileDto modernProfile = new ModernCustomerProfileDto();
            modernProfile.setId(customerId);
            modernProfile.setFirstName("Motaz");
            modernProfile.setLastName("Ahmed");
            modernProfile.setEmailAddress("new@new.com");
            modernProfile.setContactNumber("00000000000");
            return modernProfile;
        }

    }

# Benefits of This Approach

1.  Seamless Consumer Experience: Consumers remain unaffected by the
    migration process.

2.  Clear Separation of Concerns: The adapter isolates transformation
    logic.

3.  Progressive Migration: Supports gradual adoption of the new system.

4.  Scalability: Easily extendable to include additional systems.

