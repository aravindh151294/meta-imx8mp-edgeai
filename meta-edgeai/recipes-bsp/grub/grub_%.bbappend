# Ensure grub-mkconfig (invoked by cl-deploy) emits devicetree for UCM-iMX8M-Plus
# so /dev/galcore (NPU) and single-port eth0 PHY mapping survive eMMC deployment.
do_install:append:class-target() {
    if [ -f ${D}${sysconfdir}/grub.d/10_linux ]; then
        sed -i 's|^\([[:space:]]*\)\(linux[[:space:]].*root=\)|\1devicetree /boot/ucm-imx8m-plus.dtb\n\1\2|' \
            ${D}${sysconfdir}/grub.d/10_linux
        grep -q "devicetree /boot/ucm-imx8m-plus.dtb" ${D}${sysconfdir}/grub.d/10_linux \
            || bbfatal "Failed to inject devicetree line into /etc/grub.d/10_linux"
    fi
}
