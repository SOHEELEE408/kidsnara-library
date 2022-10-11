<script setup>
import { ref } from "vue";
import { StreamBarcodeReader } from "vue-barcode-reader";
const decodedText = ref("");
const onLoaded = () => {
  console.log("loaded");
};
const onDecode = (text) => {
  decodedText.value = text;

  searchData(text);
};

function searchData(result) {
  this.emitter.emit("searchData", result);
}

</script>

<template>
  <StreamBarcodeReader @decode="onDecode" @loaded="onLoaded"></StreamBarcodeReader>
  <h2>The decoded value in QR/barcode is</h2>
  <h2>{{ decodedText }}</h2>



</template>

<style scoped>
a {
  color: #42b983;
}
</style>