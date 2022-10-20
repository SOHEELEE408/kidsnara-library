<template>
  <div class="container">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">새로운 책 등록</h5>
        <form @submit.prevent="regBook">
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
            <label for="postsDiscount">가격</label>
            <input type="text"
                      class="form-control"
                      id="postsDiscount"
                      v-model="posts.discount"
                      placeholder="가격"/>
          </div>

          <div class="form-group">
            <label for="postsCount">보유 권 수</label>
            <input type="number"
                      class="form-control"
                      id="postsCount"
                      v-model="count"
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

          <div class="row mt-3 float-right">
            <div class="col-auto">
              <button class="btn btn-success"
                      type="submit">
                <i class="fa">새 도서 등록</i>
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>

    <div class="for mt-3 float-left">
      <div class="col-auto">
        <router-link :to="{path:'/books'}"
                     class="btn btn-primary">
          <i class="fa">목록으로</i>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "BooksReg",
  components: {},
  data(){
    return{
      disabled:true,
      posts: JSON.parse(localStorage.getItem('newBook')),
      count: ""
    }
  },
  methods:{
    regBook(){
      if(this.count == 0){
        this.count = 1
      }
      let params={
        "isbn": this.posts.isbn,
        "title": this.posts.title,
        "author": this.posts.author,
        "publisher": this.posts.publisher,
        "price": this.posts.discount,
        "count": this.count,
        "genre": this.posts.genre
      }
      console.log(JSON.stringify(params))
      this.axios.post('http://localhost:8081/books',
      params,
      {headers: {
          'X-ACCESS-TOKEN': localStorage.getItem('token'),
          'Content-Type':'application/json;charset=UTF-8'
          }}
      ).then(res=>{
        alert("새 책이 등록되었습니다. \n 도서명: ["+res.data.title+"]")
        localStorage.removeItem('newBook')
        this.$router.push("/books")
      }).catch((error) =>{
        alert(error.response.data.message)
      })
    }
  }
}
</script>

<style scoped>

</style>