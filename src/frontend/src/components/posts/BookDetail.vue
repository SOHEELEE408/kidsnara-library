<template>
  <div class="container">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">도서 상세</h5>
        <form>

          <div class="form-group">
            <label for="isbn">ISBN</label>
            <input type="text"
                   class="form-control"
                   id="isbn"
                   v-model="posts.isbn"
                   :disabled="disabled"/>
          </div>
          <div class="form-group">
            <label for="postsTitle">도서명</label>
            <input type="text"
                   class="form-control"
                   id="postsTitle"
                   placeholder="글 제목을 입력해주세요."
                   v-model="posts.title"
                   :disabled="disabled"/>
          </div>
          <div class="form-group">
            <label for="postsAuthor">작가</label>
            <input type="text"
                   class="form-control"
                   id="postsAuthor"
                   v-model="posts.author"
                   placeholder="작가"
                   :disabled="disabled"/>
          </div>

          <div class="form-group">
            <label for="postsPublisher">출판사</label>
            <input type="text"
                   class="form-control"
                   id="postsPublisher"
                   v-model="posts.publisher"
                   placeholder="출판사"
                   :disabled="disabled"/>
          </div>

          <div class="form-group">
            <label for="postsDiscount">가격</label>
            <input type="text"
                   class="form-control"
                   id="postsDiscount"
                   v-model="posts.discount"
                   placeholder="가격"
                   :disabled="disabled"/>
          </div>

          <div class="form-group">
            <label for="postsCount">보유 권 수</label>
            <input type="number"
                   class="form-control"
                   id="postsCount"
                   v-model="posts.count"
            />
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

    <div class="row mt-3 float-left">
      <div class="col-auto">
        <router-link :to="{path:'/books'}"
                     class="btn btn-primary">
          <i class="fa">목록으로</i>
        </router-link>
      </div>
    </div>

    <div class="row mt-3 float-right"
         v-if="disabled">
      <div class="col-auto">
        <button class="btn btn-success"
                type="button"
                @click="setPosts()">수정</button>
      </div>

      <div class="col-auto">
        <button
            class="btn btn-danger"
            type="button"
            @click="delPosts()">삭제
        </button>
      </div>

      <div class="col-auto">
        <button
            class="btn btn-danger"
            type="button"
            @click="readMode()">취소
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BookDetail",
  data(){
    return{
      disabled:true,
      bookId: this.$route.params.bookId,
      posts:{}
    }
  },
  mounted(){
    this.handleService()
  },
  methods:{
    handleService(){
      this.readMode()
      this.getPosts()
    },
    readMode(){
      this.disabled=true
    },
    setMode(){
      this.disabled=false
    },
    getPosts(){
      this.axios.get('http://localhost:8081/books/'+this.bookId,

          {headers: { 'X-ACCESS-TOKEN': localStorage.getItem('token')}}
      )
      .then(res=>{
        console.log(res.data)
        this.posts=res.data
      }).catch(e=>{
        console.log(e.response.data)
      })
    },
    setPosts(){
      let params={
        "count": this.posts.count,
        "genre": this.posts.genre
      }
      this.axios.patch('http://localhost:8081/books/'+this.bookId,
      params,
          {headers: {
              'X-ACCESS-TOKEN': localStorage.getItem('token'),
              'Content-Type':'application/json;charset=UTF-8'
            }}
      ).then(res=>{
        console.log(res.data)
        alert("수정되었습니다.")
        this.handleService()
      }).catch(e=>{
        alert(e.response.data.message)
      })
    },
    delPosts(){
      this.axios.delete('http://localhost:8081/books/'+this.bookId,
          {headers: { 'X-ACCESS-TOKEN': localStorage.getItem('token')}}
      ).then(res=>{
        console.log(res.data)
        alert("삭제되었습니다.")
        this.$router.push("/books")
      }).catch(e=>{
        alert(e.response.data.message)
      })
    }
  }
}
</script>

<style scoped>

</style>