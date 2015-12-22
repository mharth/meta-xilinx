SUMMARY = "External Xilinx toolchain"
include xilinx-toolchain.inc

PV = "${CSL_VER_MAIN}"
PKGV = "${CSL_VER_GDB}"

PR = "r1"

LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

PROVIDES = "gdbserver"

INHIBIT_DEFAULT_DEPS = "1"
INHIBIT_PACKAGE_STRIP = "1"

PACKAGES = "gdbserver gdbserver-dbg"
ALLOW_EMPTY_gdbserver-dbg = "0"
FILES_gdbserver = "${bindir}/gdbserver"
FILES_gdbserver-dbg = "${bindir}/.debug/gdbserver"

INSANE_SKIP_gdbserver = "ldflags"

do_install() {
	root="${EXTERNAL_TOOLCHAIN}"
	install -d ${D}/usr/bin

	find $root -path "*/${CSL_TARGET_SYS}/*/usr/bin/gdbserver" -print -exec cp -a {} ${D}/usr/bin \;
}

# Ensure that our rdeps are able to be set by shlibs processing
do_package[depends] = "\
    virtual/libc:do_packagedata \
    virtual/${TARGET_PREFIX}compilerlibs:do_packagedata \
"
