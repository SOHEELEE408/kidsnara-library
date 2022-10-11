<template>
  <h1>Vue Barcode Reader Demo</h1>
  <CameraScanner />
</template>

<script>
import CameraScanner from "../layout/CameraScanner";
export default {
  name: "BarcodeScanner",
  components: {CameraScanner}
}
</script>

<style scoped>

</style>