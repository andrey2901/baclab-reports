package ua.com.hedgehogsoft.baclabreports.model;

import java.io.Serializable;

public interface ModelEntity<ID extends Serializable>
{
   ID getId();
}
