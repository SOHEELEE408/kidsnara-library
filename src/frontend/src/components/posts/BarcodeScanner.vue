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
          'X-ACCESS-TOKEN': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVU0VSX0VNQUlMIjoidGVzdEBlbWFpbC5jb20iLCJpc3MiOiJraWRzbmFyYSIsIlVTRVJfUk9MRSI6IlJPTEVfVVNFUiIsImV4cCI6MTY2NTc0MDgyMH0.Kk3fy-aVkuUTmL4OdSNA3fs9_GJGjvS96KE9dRQ7-2Y'
        }
      }
  )
  .then(res => {
    alert(res)
    this.$emitter.on("searchData", res)
    this.$router.push('BookReg')
  }).catch(
      e => { alert(e)}
  )
}
</script>

<style scoped>
a {
  color: #42b983;
}
</style>