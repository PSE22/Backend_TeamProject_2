<template>
  <!-- Header -->
  <HeaderCom />
  <!-- 본문 -->
  <div class="container d-flex flex-column min-vh-100">
    <router-view/>
  </div>

  <!-- Footer -->
  <FooterCom />
</template>

<script>
import HeaderCom from "./components/common/HeaderCom.vue";
import FooterCom from "./components/common/FooterCom.vue";

export default {
  components: {
    HeaderCom,
    FooterCom
  }
}
</script>

<style>
</style>
