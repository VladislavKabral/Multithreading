package by.epam.multithreading.parser;

import by.epam.multithreading.entity.Client;

import java.util.List;

public interface Parser {
    List<Client> parse(String fileName) throws ParserException;
}
