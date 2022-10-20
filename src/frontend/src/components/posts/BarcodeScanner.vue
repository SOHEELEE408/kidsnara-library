<template>
  <h1>도서 정보 검색</h1>
  <StreamBarcodeReader @decode="onDecode" @loaded="onLoaded"></StreamBarcodeReader>
  <input type="text" v-model.trim="decodedText" />
  <button @click="searchData(decodedText)">찾기</button>
  <div class="row mt-3 float-center">
    <div class="col-auto">
      <router-link :to="{path:'/books/regist'}"
                   class="btn btn-primary">
        <i class="fa fa-plus">수동으로 등록하기</i>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { StreamBarcodeReader } from "vue-barcode-reader";
import axios from "axios";

const decodedText = ref("");
const onLoaded = () => {
  console.log("loaded");
};
const onDecode = (text) => {
  decodedText.value = text;
};

const searchData = (decodedText) => {
  console.log(decodedText);
  axios.get(
      'http://localhost:8081/books/search', {
        params: {
          isbn: decodedText
        },
        headers: {
          'X-ACCESS-TOKEN': localStorage.getItem('token')
        }
      }
  )
  .then(res => {
    console.log(res.data.items[0])
    localStorage.setItem('newBook', JSON.stringify(res.data.items[0]))
    window.location.replace('/books/regist');
  })
  .catch((error) => {
    alert(error.response);
  })
}
</script>

<style scoped>
a {
  color: #42b983;
}
</style>