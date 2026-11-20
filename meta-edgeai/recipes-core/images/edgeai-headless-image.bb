SUMMARY = "Deterministic Headless Edge AI Image with Verisilicon NPU Support"
DESCRIPTION = "Lean headless image based on NXP imx-image-core, stripped of audio/multimedia bloat, with eIQ TensorFlow Lite VX Delegate and /dev/galcore NPU runtime."
LICENSE = "MIT"

# Inherit NXP's official headless i.MX baseline image
require recipes-fsl/images/imx-image-core.bb
# Override NXP image name
IMAGE_BASENAME = "${PN}"
# Replaces the default timestamp suffix with your distro version
IMAGE_VERSION_SUFFIX = "-v${DISTRO_VERSION}"

# Strip VPU hwcodecs and splash screen features inherited from imx-image-core
IMAGE_FEATURES:remove = "hwcodecs splash"

# Remove Docker, factory stress-test (pulls imx-gpu-viv-demos), and competing network managers
IMAGE_INSTALL:remove = "docker cl-stest dhcpcd ifupdown"

# Append the eIQ NPU runtime (pulls libopenvx-imx, libvsc-imx, libnn-imx, libgal-imx via RDEPENDS)
# plus deterministic profiling
IMAGE_INSTALL:append = " \
    edgeai-network \
    imx-gpu-viv-tools \
    tensorflow-lite \
    tensorflow-lite-vx-delegate \
    tim-vx \
    htop \
    tmux \
    rsync \
    openssh-sftp-server \
    podman \
    podman-compose \
    os-release \
"