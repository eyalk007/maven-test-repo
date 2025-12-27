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

## Test Case 3: Parent POM Update ⚠️

**Status:** ⚠️ SKIPPED - Engine Limitation

**Issue:** Engine cannot resolve versions inherited from parent POMs (returns `version: unknown`)

**Requires:** Engine enhancement to fetch and parse parent POMs from Maven repositories

---

## Test Case 4: DependencyManagement Update ✅

**Status:** ✅ PASSED

**Result:** Frogbot successfully updated `log4j:1.2.17` → `1.2.17-atlassian-0.4` in dependencyManagement section

---

## Test Case 5: Multi-Module Project ✅

**Status:** ✅ PASSED

**Project Structure:**
```
maven-test-repo/
├── pom.xml                    (parent/aggregator)
├── backend/
│   └── pom.xml               (commons-collections:3.2.1 - vulnerable)
└── frontend/
    └── pom.xml               (commons-collections:3.2.1 - vulnerable)
```

**Vulnerable Dependency (same in both modules):**
- `commons-collections:commons-collections:3.2.1`
- Fix version: `3.2.2`

**Result:** ✅ PASSED
- Frogbot detected the SAME vulnerability in BOTH modules
- Engine returned 2 `ComponentRow` entries:
  - `Component[0].Location.File = "backend/pom.xml"`
  - `Component[1].Location.File = "frontend/pom.xml"`
- Frogbot created **ONE PR** updating **BOTH files**:
  - `backend/pom.xml`: `3.2.1` → `3.2.2`
  - `frontend/pom.xml`: `3.2.1` → `3.2.2`
- **Text-based replacement preserved all formatting!**
  - No lost namespaces
  - No lost fields
  - No reformatting
  - Only version numbers changed

**Key Achievement:** Multi-module support with minimal, clean diffs (like Renovate/Dependabot)

---

## Test Case 6: Non-Standard POM Names ❌

**Status:** ❌ ENGINE LIMITATION

**Test File:** `pom-dev.xml`
```xml
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

**Expected:** Engine scans `pom-dev.xml` and detects `log4j:1.2.17`

**Actual Result:** ❌ Engine does NOT scan `pom-dev.xml`
- SBOM only includes dependencies from standard `pom.xml` files
- `log4j:1.2.17` not detected

**SBOM Output:**
```
"gav://com.example:backend:1.0.0",
"gav://com.example:frontend:1.0.0",
"gav://commons-collections:commons-collections:3.2.1"
```
Missing: `gav://log4j:log4j:1.2.17`

**Industry Practice:**
- Projects use `pom-dev.xml`, `pom-prod.xml`, `pom-test.xml` for different environments
- **Renovate supports** via regex: `/(^|/|\.)pom\.xml$/`
- **Dependabot supports** non-standard pom names
- **JFrog Engine does NOT** ❌

**Handler Support:** ✅ Maven handler would work if engine provided the file path  
**Engine Support:** ❌ Engine does not scan non-standard pom file names

**Impact:** Enterprise projects using environment-specific POMs won't have those files scanned for vulnerabilities

**Recommendation:** Engine should scan all Maven POM patterns, not just `pom.xml`

---

## Test Summary

| Test Case | Feature | Status |
|-----------|---------|--------|
| 1. Simple Dependency | Direct `<version>` update | ✅ PASSED |
| 2. Property Version | `${property}` resolution | ✅ PASSED |
| 3. Parent POM | Inherited versions | ⚠️ ENGINE LIMITATION |
| 4. DependencyManagement | Centralized versions | ✅ PASSED |
| 5. Multi-Module | Multiple files, one PR | ✅ PASSED |
| 6. Non-Standard POMs | pom-dev.xml, pom-prod.xml | ❌ ENGINE LIMITATION |

**Maven Package Updater: 4/6 scenarios (67% coverage)**

**Handler is feature-complete!** All failures are engine limitations, not handler issues.

---

## Engine Limitations Summary

1. **Parent POM Resolution** - Cannot resolve versions from external parent POMs
2. **Non-Standard POM Names** - Only scans `pom.xml`, not `pom-*.xml` patterns

**Both are common enterprise Maven practices that Renovate/Dependabot support.**
