do_install:append() {
    for cfg in "${D}/boot/grub/grub.cfg" "${D}${datadir}/compulab/grub-bootconf-yocto"; do
        if [ -f "${cfg}" ]; then
            sed -i 's/# echo "devicetree/echo "devicetree/g; s/# devicetree/devicetree/g' "${cfg}"
        fi
    done
}
