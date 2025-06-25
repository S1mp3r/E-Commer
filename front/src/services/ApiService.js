import { Base } from "./Base";

export class ApiService extends Base {
  static signUp({ email, password }) {
    return super.post({
      url: "/v1/auth/register",
      data: {
        email: email,
        password: password,
      },
      isPublic: true,
    });
  }

  static signIn({ email, password }) {
    return super.post({
      url: "/v1/auth/login",
      data: { email: email, password: password, role: "USER" },
      isPublic: true,
    });
  }
}
