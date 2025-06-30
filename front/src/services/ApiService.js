import { BaseUrl } from "./BaseUrl";

export class ApiService extends BaseUrl {
  static signUp({ email, password }) {
    return super.post({
      url: "/api/v1/auth/register",
      data: {
        email: email,
        password: password,
      },
      isPublic: true,
    });
  }

  static signIn({ email, password }) {
    return super.post({
      url: "/api/v1/auth/login",
      data: { email: email, password: password, role: "USER" },
      isPublic: true,
    });
  }
}
