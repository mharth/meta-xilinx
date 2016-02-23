SUMMARY = "External Xilinx toolchain"
include xilinx-toolchain.inc

PV = "${CSL_VER_MAIN}"
PKGV = "${CSL_VER_GCC}"

PR = "r1"

LICENSE = "GPL-3.0-with-GCC-exception"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-with-GCC-exception;md5=aef5f35c9272f508be848cd99e0151df"
LICENSE_libgcov-dev = "GPL-3.0"
LIC_FILES_CHKSUM_libgcov-dev = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

DEPENDS = "virtual/${TARGET_PREFIX}gcc virtual/${TARGET_PREFIX}g++ virtual/${TARGET_PREFIX}binutils"
PROVIDES = "libgcc"

INHIBIT_DEFAULT_DEPS = "1"

PACKAGES = "libgcc libgcc-dev libgcc-dbg libgcov-dev"

RDEPENDS_libgcc += "${TCLIBC} glibc-xlnx"
INSANE_SKIP_libgcc += "build-deps ldflags"
INSANE_SKIP_libgcc-dev += "builds-deps staticdev"
INSANE_SKIP_${MLPREFIX}libgcov-dev += "build-deps staticdev"

FILES_libgcc = "${base_libdir}/libgcc*.so.*"

FILES_libgcc-dev = "${base_libdir}/libgcc*.so \
		   ${libdir}/${TARGET_SYS}/${BINV}* \
                   ${libdir}/${CSL_TARGET_SYS}/${CSL_VER_GCC}/*crt* \
		   ${libdir}/${CSL_TARGET_SYS}/${CSL_VER_GCC}/libgcc.a \
		   ${libdir}/${CSL_TARGET_SYS}/${CSL_VER_GCC}/libgcc_eh.a \
                   "
FILES_libgcc-dbg = "${base_libdir}/.debug/ ${libdir}/${CSL_TARGET_SYS}/${CSL_VER_GCC}/.debug/"

FILES_libgcov-dev = "${libdir}/${CSL_TARGET_SYS}/${CSL_VER_GCC}/libgcov.a"

do_install() {
	root="${EXTERNAL_TOOLCHAIN}"

	install -d ${D}/${base_libdir} \
		 ${D}/${libdir}/${CSL_TARGET_SYS}/${CSL_VER_GCC}

	find $root -path "*/${CSL_TARGET_SYS}/*/*libgcc*.so*" -exec cp -a {} ${D}/${base_libdir} \;

	cp -a ${root}/lib/gcc/${CSL_TARGET_SYS}/${CSL_VER_GCC}/*crt*  \
	   ${D}/${libdir}/${CSL_TARGET_SYS}/${CSL_VER_GCC}

	cp -a ${root}/lib/gcc/${CSL_TARGET_SYS}/${CSL_VER_GCC}/libgcc*  \
	   ${D}/${libdir}/${CSL_TARGET_SYS}/${CSL_VER_GCC}

	cp -a ${root}/lib/gcc/${CSL_TARGET_SYS}/${CSL_VER_GCC}/libgcov.a  \
	   ${D}/${libdir}/${CSL_TARGET_SYS}/${CSL_VER_GCC}

	find  ${D}/${libdir}/${CSL_TARGET_SYS}/${CSL_VER_GCC}  -name "*crt*.o" -exec chmod -x {} \;
	find  ${D}/${libdir}/${CSL_TARGET_SYS}/${CSL_VER_GCC}  -name "libg*.a" -exec chmod -x {} \;
}
