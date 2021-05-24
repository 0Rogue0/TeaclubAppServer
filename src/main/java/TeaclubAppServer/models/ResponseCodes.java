package TeaclubAppServer.models;

public enum ResponseCodes {
    messageAllRight, //Ошибок не произошло
    messageAlreadyExists, //Данный пользователь уже имеется в БД
    messageHasNotMatched, //Пароль не повторен или его повторили неправильно
    messageUnsuccessfullDel, //Ошибка при удалении из БД
    messageUnsuccessfullAdd, //Ошибка при добавлении в БД
    getMessageUnsuccessfullShopCartGet, //Ошибка при получении списка корзин
    getMessageUnsuccessfullShopCartIdGet //Ошибка при получении корзины по Id
}
