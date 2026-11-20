#!/bin/sh
set -eu

# 1. Only run if eth0 is currently on the static fallback profile
ACTIVE_CON=$(nmcli -t -f GENERAL.CONNECTION device show eth0 2>/dev/null | cut -d: -f2-)
[ "${ACTIVE_CON}" = "eth0-fallback" ] || exit 0

# 2. Abort if any established TCP session (SSH, HTTP, Modbus) is active on 10.1.1.10
if ss -tnH state established src 10.1.1.10 2>/dev/null | grep -q .; then
    exit 0
fi

# 3. Retry DHCP; restore fallback immediately if no DHCP server responds
nmcli connection up eth0-dhcp >/dev/null 2>&1 || nmcli connection up eth0-fallback >/dev/null 2>&1
