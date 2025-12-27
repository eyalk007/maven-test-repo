# maven-test-repo

Maven test repository for Frogbot integration testing.

## Test Case 1: Simple Vulnerable Dependency ✅

**Status:** ✅ PASSED

**Result:** Frogbot successfully created PR updating `commons-collections:3.2.1` → `3.2.2`

---

## Test Case 2: Property-Based Version ✅

**Status:** ✅ PASSED

**Result:** Frogbot successfully updated property `<jackson.version>2.9.8</jackson.version>` → `<jackson.version>2.16.0</jackson.version>`

---

## Test Case 3: Parent POM Update

**Status:** 🧪 Ready for testing

**Vulnerable Parent POM:**
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.5.0</version>  <!-- Vulnerable -->
</parent>
```

**Known Issues:**
- Spring Boot 2.5.0 has multiple CVEs in transitive dependencies
- Fix version: 2.7.0+

**What to test:**
1. Frogbot should detect vulnerabilities in dependencies inherited from parent POM
2. Frogbot should update the **parent version** in `<parent><version>` tag
3. Verify pom.xml parent is updated: `<version>2.5.0</version>` → `<version>2.7.x</version>`

**Expected behavior:**
The updater should detect that the vulnerable dependency comes from the parent POM and update the parent version directly.

---

## Future Test Cases (TODO)

- Test Case 4: DependencyManagement
- Test Case 5: Multi-module project
