SUMMARY = "Deterministic single-port NetworkManager config for Edge AI gateway"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

S = "${WORKDIR}"

SRC_URI = " \
    file://eth0-dhcp.nmconnection \
    file://eth0-fallback.nmconnection \
    file://10-edgeai-network.conf \
    file://edgeai-dhcp-retry.sh \
    file://edgeai-dhcp-retry.service \
    file://edgeai-dhcp-retry.timer \
"

SYSTEMD_SERVICE:${PN} = "edgeai-dhcp-retry.timer"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

RDEPENDS:${PN} = " \
    networkmanager \
    networkmanager-nmcli \
    iproute2-ss \
"

do_install() {
    # NetworkManager strictly requires 0600 (root:root) on system-connections keyfiles
    install -d -m 0755 ${D}${sysconfdir}/NetworkManager/system-connections
    install -m 0600 ${WORKDIR}/eth0-dhcp.nmconnection ${D}${sysconfdir}/NetworkManager/system-connections/
    install -m 0600 ${WORKDIR}/eth0-fallback.nmconnection ${D}${sysconfdir}/NetworkManager/system-connections/

    install -d -m 0755 ${D}${sysconfdir}/NetworkManager/conf.d
    install -m 0644 ${WORKDIR}/10-edgeai-network.conf ${D}${sysconfdir}/NetworkManager/conf.d/

    install -d -m 0755 ${D}${bindir}
    install -m 0755 ${WORKDIR}/edgeai-dhcp-retry.sh ${D}${bindir}/edgeai-dhcp-retry

    install -d -m 0755 ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/edgeai-dhcp-retry.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/edgeai-dhcp-retry.timer ${D}${systemd_system_unitdir}/
}

FILES:${PN} += " \
    ${sysconfdir}/NetworkManager \
    ${bindir}/edgeai-dhcp-retry \
    ${systemd_system_unitdir} \
"