<template>
  <v-container v-if="(dataFetch)">
    <v-flex>
      <v-card class="py-15 px-12 secondary text-center" rounded>
        <p class="text-h6">Music</p>
        <v-row>
          <v-text-field
              v-model="searchMusic"
              label="Search a music by title or a category..."
              class="mx-4"
          >
          </v-text-field>
          <v-btn icon v-on:click="searchByM">
            <v-icon>search</v-icon>
          </v-btn>

        </v-row>
        <v-row>
          <v-col
              v-for="n in ncheckMusic"
              :key="n"
              cols="10"
              md="3"
          >
            <v-card class="mx-5 my-5 primary" outlined>
              <p>
                <v-img :src=music[musicIndex+n].cover aspect-ratio="0.7"></v-img>
                {{ music[musicIndex + n].title }} <br>
                {{ music[musicIndex + n].artist }}
              </p>
              <v-btn v-on:click="navToDetails('music', music[musicIndex+n].resourceId)" class="secondary white--text">
                see
                details
              </v-btn>
            </v-card>
          </v-col>
        </v-row>
        <v-btn v-on:click="musicIndex -= 4"><span class="text-h6 text--primary">←&nbsp;</span></v-btn>
        <v-btn v-on:click="musicIndex += 4"><span class="text-h6 text--primary">&nbsp;→</span></v-btn>
      </v-card>

      <v-card class="my-12 py-15 px-12 secondary text-center" rounded>
        <p class="text-h6">Books</p>
        <v-row>
          <v-text-field
              v-model="searchBook"
              label="Search a book by title or a category..."
              class="mx-4"
          >
          </v-text-field>
          <v-btn icon v-on:click="searchByB">
            <v-icon>search</v-icon>
          </v-btn>
        </v-row>
        <v-row>
          <v-col
              v-for="n in ncheckBook"
              :key="n"
              cols="10"
              md="3"
          >
            <v-card class="mx-5 my-5 primary" outlined>
              <p>
                <v-img :src=books[bookIndex+n].cover aspect-ratio="0.7"></v-img>
                {{ books[bookIndex + n].title }} <br>
                {{ books[bookIndex + n].author }}
              </p>
              <v-btn v-on:click="navToDetails('book', books[bookIndex+n].resourceId)" class="secondary white--text"> see
                details
              </v-btn>
            </v-card>
          </v-col>

        </v-row>
        <v-btn v-on:click="bookIndex-= 4"><span class="text-h6 text--primary">←&nbsp;</span></v-btn>
        <v-btn v-on:click="bookIndex += 4"><span class="text-h6 text--primary">&nbsp;→</span></v-btn>
      </v-card>

      <v-card class="my-12 py-15 px-12 secondary text-center" rounded>
        <p class="text-h6">Movies</p>
        <v-row>
          <v-text-field
              v-model="searchMovie"
              label="Search a movie by title or a category..."
              class="mx-4"
          >
          </v-text-field>
          <v-btn icon v-on:click="searchByMv">
            <v-icon>search</v-icon>
          </v-btn>
        </v-row>
        <v-row>
          <v-col
              v-for="n in ncheckMovie"
              :key="n"
              cols="10"
              md="3"
          >
            <v-card class="mx-5 my-5 primary" outlined>
              <p>
                <v-img :src="'https://www.themoviedb.org/t/p/w1280/' + movies[movieIndex+n].poster"
                       aspect-ratio="0.7"></v-img>
                {{ movies[movieIndex + n].title }} <br>
                Language: {{ movies[movieIndex + n].language }}
              </p>
              <v-btn v-on:click="navToDetails('movie', movies[movieIndex+n].resourceId)" class="secondary white--text">
                see
                details
              </v-btn>
            </v-card>
          </v-col>
        </v-row>
        <v-btn v-on:click="movieIndex -= 4"><span class="text-h6 text--primary">←&nbsp;</span></v-btn>
        <v-btn v-on:click="movieIndex += 4"><span class="text-h6 text--primary">&nbsp;→</span></v-btn>
      </v-card>
    </v-flex>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      music: [],
      books: [],
      movies: [],
      bookIndex: -1,
      movieIndex: -1,
      musicIndex: -1,
      searchMusic: "",
      searchBook: "",
      searchMovie: "",
      dataFetch: false,
      ncheckMusic: 4,
      ncheckBook: 4,
      ncheckMovie: 4
    }
  },
  mounted() {
    this.getItems();
    //await this.clearSearch();
  },
  methods: {
    async getItems() {
      if (this.searchMusic === "" && this.searchBook === "" && this.searchMovie === "") {
        const music = await this.axios.get('http://localhost:9000/quirec-api/browse/music');
        for (let element of music.data) {
          this.music.push(element)
        }
        const books = await this.axios.get('http://localhost:9000/quirec-api/browse/book');
        for (let element of books.data) {
          this.books.push(element)
        }
        const movies = await this.axios.get('http://localhost:9000/quirec-api/browse/movie');
        for (let element of movies.data) {
          this.movies.push(element)
        }
      }
      this.dataFetch = true;
    },
    navToDetails(category, resourceId) {
      this.$store.commit('setShowRating', false)
      this.$router.push({path: 'details/' + category + "/" + resourceId});
    },
    searchByM() {
      if (this.searchMusic.length !== 0) {
        let i = 0;
        this.axios.get('http://localhost:9000/quirec-api/browse/musicSearch/' + this.searchMusic).then(
            response => {
              this.music.splice(0);
              this.musicIndex = -1;
              for (let element of response.data) {
                this.$set(this.music, i, element)
                ++i;
              }
              if (this.music.length < 4) {
                this.ncheckMusic = this.music.length
              } else
                this.ncheckMusic = 4;
            })
      }
    },
    searchByB() {
      if (this.searchBook.length !== 0) {
        let i = 0;
        this.axios.get('http://localhost:9000/quirec-api/browse/bookSearch/' + this.searchBook).then(
            response => {
              this.books.splice(0);
              this.bookIndex = -1;
              for (let element of response.data) {
                this.$set(this.books, i, element)
                ++i;
              }
              if (this.books.length < 4) {
                this.ncheckBook = this.books.length
              } else
                this.ncheckBook = 4;
            })
      }
    },
    searchByMv() {
      if (this.searchMovie.length !== 0) {
        let i = 0;
        this.axios.get('http://localhost:9000/quirec-api/browse/movieSearch/' + this.searchMovie).then(
            response => {
              this.movies.splice(0);
              this.movieIndex = -1;
              for (let element of response.data) {
                this.$set(this.movies, i, element)
                ++i;
              }
              if (this.movies.length < 4) {
                this.ncheckMovie = this.movies.length
              } else
                this.ncheckMovie = 4;
            })
      }
    }
  }
};
</script>
