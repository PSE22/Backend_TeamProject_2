<template>
  <header class="header">
    <div class="header-top">
      <div class="header-left">
        <input type="text" placeholder="검색어를 입력하세요" class="search-input" v-model="searchQuery"/>
        <button @click="executeSearch" class="search-button">검색</button>
      </div>
      <div class="header-center">
        <h1>서울쥐</h1>
      </div>
      <div class="header-right">
        <a href="#">로그인</a>
        <a href="#">회원가입</a>
      </div>
    </div>
    <nav class="header-nav">
      <ul>
        <li><a href="#">부서 게시판</a></li>
        <li><a href="#">동호회 게시판</a></li>
        <li><router-link to="/free" class="router-link">자유 게시판</router-link></li>
        <li><a href="#">건의 게시판</a></li>
        <li><a href="#">칭찬 게시판</a></li>
      </ul>
    </nav>
  </header>
</template>
  <script>
  export default {
  };
  </script>
  
  <style scoped>
@import "@/assets/css/home.css";
  </style>