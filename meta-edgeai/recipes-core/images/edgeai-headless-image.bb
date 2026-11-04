SUMMARY = "Deterministic Headless Edge AI Image with Verisilicon NPU Support"
DESCRIPTION = "Lean headless image based on NXP imx-image-core, stripped of audio/multimedia bloat, with eIQ TensorFlow Lite VX Delegate and /dev/galcore NPU runtime."
LICENSE = "MIT"

# Inherit NXP's official headless i.MX baseline image
require recipes-fsl/images/imx-image-core.bb

# 1. Strip unneeded audio, multimedia, 3D demos, and wireless packagegroups from imx-image-core
IMAGE_INSTALL:remove = " \
    packagegroup-fsl-tools-audio \
    packagegroup-fsl-gstreamer1.0 \
    packagegroup-fsl-gstreamer1.0-full \
    imx-gpu-viv-demos \
    alsa-utils \
    alsa-state \
    packagegroup-base-wifi \
    packagegroup-base-bluetooth \
    docker \
"

# 2. Append the eIQ NPU runtime (pulls libopenvx-imx, libvsc-imx, libnn-imx, libgal-imx via RDEPENDS)
#    plus deterministic profiling and Antigravity IDE deployment utilities
IMAGE_INSTALL:append = " \
    imx-gpu-viv \
    imx-gpu-viv-tools \
    tensorflow-lite \
    tensorflow-lite-vx-delegate \
    tim-vx \
    htop \
    tmux \
    rsync \
    openssh-sftp-server \
    kernel-modules \
    podman \
    podman-compose \
"