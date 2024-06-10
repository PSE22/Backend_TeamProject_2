import http from "@/utils/http-common";
import LoginHeader from "@/services/login/LoginHeader";

class ReportService {
  getAll(page, size) {
    return http.get(`/admin/report?page=${page}&size=${size}`, {
      headers: LoginHeader(),
    });
  }
}

export default new ReportService();