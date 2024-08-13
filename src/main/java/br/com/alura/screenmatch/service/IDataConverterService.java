package br.com.alura.screenmatch.service;

public interface IDataConverterService {
    <T> T getData(String json, Class<T> clazz);
}
