package ar.edu.utn.frbb.tup;

import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaSoportadaException;
import ar.edu.utn.frbb.tup.presentation.input.MenuInputProcessor;

import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class Application {

    public static void main(String[] args) throws TipoCuentaSoportadaException {
        try (ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class)) {
            MenuInputProcessor processor = applicationContext.getBean(MenuInputProcessor.class);

            processor.renderMenu();
        } catch (BeansException | ClienteAlreadyExistsException e) {
            e.printStackTrace();
        }
    }
}