import { BaseUrl } from "./BaseUrl";

export class ApiService extends BaseUrl {
  static signUp({ email, password }) {
    return super.post({
      url: "/auth/v1/register",
      data: {
        email: email,
        password: password,
      },
      isPublic: true,
    });
  }

  static signIn({ email, password }) {
    return super.post({
      url: "/auth/v1/login",
      data: { email: email, password: password, role: "USER" },
      isPublic: true,
    });
  }

  static async logout() {
    return super.delete({
      url: "/auth/v1/logout",
    });
  }

  static test() {
    return super.get({
      url: "/ecommerce/api/v1/init",
    });
  }
}
