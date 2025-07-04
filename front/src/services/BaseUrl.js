import axios from "axios";

export class BaseUrl {
  static onError = null;

  static setOnError(callback) {
    BaseUrl.onError = callback;
  }

  static _getRequestConfig(isPublic = false) {
    return {
      headers: {
        Authorization: !isPublic
          ? `Bearer ${localStorage.getItem("token")}`
          : "",
      },
    };
  }

  static async get({ url, isPublic }) {
    let urlToSend = process.env.NEXT_PUBLIC_BASE_URL + url;
    return axios
      .get(urlToSend, BaseUrl._getRequestConfig(isPublic))
      .catch((error) => BaseUrl.handleError(error));
  }

  static async post({ url, data, isPublic }) {
    let urlToSend = process.env.NEXT_PUBLIC_BASE_URL + url;
    return axios
      .post(urlToSend, data, BaseUrl._getRequestConfig(isPublic))
      .catch((error) => BaseUrl.handleError(error));
  }

  static async put({ url, data, isPublic }) {
    let urlToSend = process.env.NEXT_PUBLIC_BASE_URL + url;
    return axios
      .put(urlToSend, data, BaseUrl._getRequestConfig(isPublic))
      .catch((error) => BaseUrl.handleError(error));
  }

  static async patch({ url, data, isPublic }) {
    let urlToSend = process.env.NEXT_PUBLIC_BASE_URL + url;
    return axios
      .patch(urlToSend, data, BaseUrl._getRequestConfig(isPublic))
      .catch((error) => BaseUrl.handleError(error));
  }

  static async delete({ url, isPublic }) {
    let urlToSend = process.env.NEXT_PUBLIC_BASE_URL + url;
    return axios
      .delete(urlToSend, BaseUrl._getRequestConfig(isPublic))
      .catch((error) => BaseUrl.handleError(error));
  }

  static handleError(error) {
    const exception = error?.response?.data;
    console.log("Requisição fracassou: ", exception);

    if (
      error?.response?.status === 401 &&
      typeof BaseUrl.onError === "function"
    ) {
      localStorage.removeItem("token");
      BaseUrl.onError();
    }

    throw exception ? exception?.error : exception;
  }
}
