<template>
    <div class="board-management-container">
      <h1>CM Code List</h1>
      <table class="table">
        <thead>
          <tr>
            <th>CM_CD</th>
            <th>UP_CM_CD</th>
            <th>CM_CD_NAME</th>
            <th>CM_CD_COMMENT</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in cmCodeList" :key="index">
            <td>{{ item.cmCd }}</td>
            <td>{{ item.upCmCd }}</td>
            <td>{{ item.cmCdName }}</td>
            <td>{{ item.cmCdComment }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  import BoardManageService from '@/services/board/BoardManageService';
  
  export default {
    data() {
      return {
        cmCodeList: []
      };
    },
    created() {
      this.retrieveCmCodeList();
    },
    methods: {
      async retrieveCmCodeList() {
        try {
          const response = await BoardManageService.getAll();
          this.cmCodeList = response.data;
        } catch (error) {
          console.error('Error fetching cm code list:', error);
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .board-management-container {
    padding: 20px;
  }
  
  .table {
    width: 100%;
    border-collapse: collapse;
  }
  
  .table th, .table td {
    border: 1px solid #ddd;
    padding: 8px;
  }
  
  .table th {
    background-color: #f2f2f2;
  }
  </style>
  