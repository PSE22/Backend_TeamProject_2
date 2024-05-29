import http from '@/utils/http-common';

    class MainSearchService {
        searchBoards(boardTitle, page, size) {
          return http.get('/board/main-search', {
            params: {
              boardtitle: boardTitle,
              page: page,
              size: size,
              sort: 'addDate,desc'
            }
          });
        }
      }

export default new MainSearchService();