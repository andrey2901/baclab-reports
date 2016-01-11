package ua.com.hedgehogsoft.baclabreports.ui.swing.frame;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;

public abstract class Frame
{
   protected @Autowired MessageByLocaleService messageByLocaleService;

   @PostConstruct
   protected abstract void localize();
}
