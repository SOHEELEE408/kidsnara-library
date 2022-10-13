<template>
  <div class="container">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">새로운 책 등록</h5>

        <div class="row mt-3 float-right">
          <div class="col-auto">
            <button class="btn btn-success"
                    type="button"
                    @click="searchBook()">
              <i class="fa">책 검색</i>
            </button>
          </div>
        </div>
        <div v-if="!disabled">
          <CameraScanner />
        </div>
        <form>
          <div class="form-group">
            <label for="postsIsbn">ISBN</label>
            <input type="text"
                   class="form-control"
                   id="postsIsbn"
                   v-model="posts.isbn"
                   placeholder="ISBN"/>
          </div>


          <div class="form-group">
            <label for="postsTitle">도서명</label>
            <input type="text"
                   class="form-control"
                   id="postsTitle"
                   v-model="posts.title"
                   placeholder="도서명을 입력해주세요."/>
          </div>

          <div class="form-group">
            <label for="postsAuthor">작가</label>
            <input type="text"
                      class="form-control"
                      id="postsAuthor"
                      v-model="posts.author"
                      placeholder="작가"/>
          </div>

          <div class="form-group">
            <label for="postsPublisher">출판사</label>
            <input type="text"
                      class="form-control"
                      id="postsPublisher"
                      v-model="posts.publisher"
                      placeholder="출판사"/>
          </div>

          <div class="form-group">
            <label for="postsPrice">가격</label>
            <input type="text"
                      class="form-control"
                      id="postsPrice"
                      v-model="posts.price"
                      placeholder="가격"/>
          </div>

          <div class="form-group">
            <label for="postsCount">보유 권 수</label>
            <input type="text"
                      class="form-control"
                      id="postsCount"
                      v-model="posts.count"
                      placeholder="보유 권 수 "/>
          </div>

          <div class="form-group">
            <label for="postsGenre">장르</label>
            <input type="text"
                      class="form-control"
                      id="postsGenre"
                      v-model="posts.genre"
                      placeholder="장르"/>
          </div>
        </form>
      </div>
    </div>

    <div class="for mt-3 float-left">
      <div class="col-auto">
        <router-link :to="{path:'/barcode'}"
                     class="btn btn-primary">
          <i class="fa">책 검색</i>
        </router-link>
      </div>
    </div>

    <div class="for mt-3 float-left">
      <div class="col-auto">
        <router-link :to="{path:'/posts'}"
                     class="btn btn-primary">
          <i class="fa">목록으로</i>
        </router-link>
      </div>
    </div>

    <div class="row mt-3 float-right">
      <div class="col-auto">
        <button class="btn btn-success"
                type="button"
                @click="regPosts()">
          <i class="fa">저장</i>
        </button>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "PostsReg",
  components: {},
  data(){
    return{
      disabled:true,
      posts:{}
    }
  },
  methods:{
    regPosts(){
      let params={
        "author":this.author,
        "title":this.title,
        "content":this.content,
      }
      this.axios.post('http://localhost:9000/api/v1/posts',
      JSON.stringify(params), {headers:{'content-type':'application/json'}}
      ).then(res=>{
        alert("새 글이 등록되었습니다. \n 글번호: ["+res.data+"]")
        this.$router.push("/posts")
      }).catch(e=>{
        alert(e.response.data)
      })
    },
    searchBook(){
      this.disabled=false
      this.emitter.on('searchData',
          result => {
            this.axios.get('http://localhost:8080/books/isbn-search/'+ result)
                .then(res => {
                  alert(res.data)
                  this.posts = res.data
                }).catch(e=>{
              console.log(e)
            })
          })
    }
  }
}
</script>

<style scoped>

</style>