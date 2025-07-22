import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/v1")
public class TestRestController {
    @GetMapping("/users/{guid:.+}")
    public ResponseEntity<UsersResponse> getUsers(
            HttpServletRequest request,
            @PathVariable String guid,
            @RequestParam(value = "entitlements", required = false) Set<String> entitlements,
            @RequestParam(value = "fields", required = false, defaultValue = "guid,isEmployee,entitlements,otherUserIdentifiers") Set<String> fields
    ) {
        return ResponseEntity.ok(new UsersResponse());
    }

    @GetMapping("/users/{guid:.+}/region")
    public ResponseEntity<UsersResponse> getRegionInfo(
            HttpServletRequest request,
            @PathVariable String guid
    ) {
        return ResponseEntity.ok(new UsersResponse());
    }

    // Example: GET /entitlements/v1/users/{guid}/entitlement/{entitlement}
    @GetMapping("/users/{guid:.+}/entitlement/{entitlement:.+}")
    public ResponseEntity<UsersResponse> getUserEntitlementInfo(
            HttpServletRequest request,
            @PathVariable String guid,
            @PathVariable String entitlement
    ) {
        return ResponseEntity.ok(new UsersResponse());
    }

    @GetMapping("/users")
    public ResponseEntity<UsersResponse> getUsers(
            HttpServletRequest request,
            UserSelector selector
    ) {
        return ResponseEntity.ok(new UsersResponse());
    }
 
    @PostMapping("/users")
    public ResponseEntity<UsersEntitlementsResponse> getUserEntitlements(
            HttpServletRequest request,
            @RequestBody UserSelector selector
    ) {
        return ResponseEntity.ok(new UsersEntitlementsResponse());
    } 
    @GetMapping("/entitlements/{entitlement:.+}")
    public ResponseEntity<EntitlementsResponse> getEntitlementsAssignmentByPath(
            HttpServletRequest request,
            EntitlementSelector selector
    ) {
        return ResponseEntity.ok(new EntitlementsResponse());
    } 
    @GetMapping("/entitlements1")
    public ResponseEntity<EntitlementsResponse> getEntitlementsAssignmentByQuery(
            HttpServletRequest request,
            @RequestParam("entitlements") Set<String> entitlements
    ) {
        return ResponseEntity.ok(new EntitlementsResponse());
    } 
    @GetMapping("/contacts1")
    public ResponseEntity<ContactResponse> getContacts(
            HttpServletRequest request,
            @RequestParam("normalizedNumber") String normalizedNumber
    ) {
        return ResponseEntity.ok(new ContactResponse());
    }
}
