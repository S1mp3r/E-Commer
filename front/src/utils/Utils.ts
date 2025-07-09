class Utils {
  static dateToBrazilianDate(date: string) {
    return date.split("-").reverse().join("/");
  }

  static maskToCpf(cpf: string) {
    return cpf
      .replace(/\D/g, "")
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d{1,2})$/, "$1-$2");
  }

  static cellPhoneMask(cellPhoneNumber: string) {
    return cellPhoneNumber
      .replace(/\D/g, "")
      .replace(/(\d{3})(\d)/, "($1) $2")
      .replace(/(\d{5})(\d)/, "$1-$2");
  }
}

export default Utils;
