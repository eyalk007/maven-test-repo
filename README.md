# maven-test-repo

Maven test repository for Frogbot integration testing.

## Test Case 1: Simple Vulnerable Dependency

**Status:** ✅ Ready for testing

**Vulnerable Dependency:**
- `commons-collections:commons-collections:3.2.1`
- Known CVEs: Multiple deserialization vulnerabilities
- Fix version: `3.2.2`

**What to test:**
1. Frogbot should detect the vulnerability
2. Frogbot should create a fix PR updating `3.2.1` → `3.2.2`
3. Verify pom.xml is updated correctly

---

## Future Test Cases (TODO)

- Test Case 2: Property-based version (`${...}`)
- Test Case 3: Parent POM update
- Test Case 4: DependencyManagement
- Test Case 5: Multi-module project
