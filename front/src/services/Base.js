import axios from "axios";

export class Base {
  static _getRequestConfig(isPublic) {
    return {
      headers: {
        Authorization: isPublic ? "" : localStorage.getItem("token"),
      },
    };
  }

  static async get({ url, isPublic }) {
    let urlToSend = process.env.NEXT_PUBLIC_BASE_URL + url;
    return axios
      .get(urlToSend, Base._getRequestConfig(isPublic))
      .catch((error) => console.log("Requisição fracassou: ", error));
  }

  static async post({ url, data, isPublic }) {
    let urlToSend = process.env.NEXT_PUBLIC_BASE_URL + url;
    return axios
      .post(urlToSend, data, Base._getRequestConfig(isPublic))
      .catch((error) => console.log("Requisição fracassou: ", error));
  }

  static async put({ url, data, isPublic }) {
    let urlToSend = process.env.NEXT_PUBLIC_BASE_URL + url;
    return axios
      .put(urlToSend, data, Base._getRequestConfig(isPublic))
      .catch((error) => console.log("Requisição fracassou: ", error));
  }

  static async patch({ url, data, isPublic }) {
    let urlToSend = process.env.NEXT_PUBLIC_BASE_URL + url;
    return axios
      .patch(urlToSend, data, Base._getRequestConfig(isPublic))
      .catch((error) => console.log("Requisição fracassou: ", error));
  }

  static async delete({ url }) {
    let urlToSend = process.env.NEXT_PUBLIC_BASE_URL + url;
    return axios
      .delete(urlToSend)
      .catch((error) => console.log("Requisição fracassou: ", error));
  }
}
