CSL_VER_MAIN ??= ""

python () {
    if not d.getVar("CSL_VER_MAIN"):
        raise bb.parse.SkipPackage("External toolchain not configured (CSL_VER_MAIN not set).")
}

