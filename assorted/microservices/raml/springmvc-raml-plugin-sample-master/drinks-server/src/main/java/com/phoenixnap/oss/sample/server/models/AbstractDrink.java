/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.phoenixnap.oss.sample.server.models;

import com.phoenixnap.oss.sample.server.models.enums.DrinkTypeEnum;

/**
 * Abstract representation of a drink. Adds some simple fields to work with. 
 * @author kristiang
 *
 */
public abstract class AbstractDrink {
    
    private String name;
    
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public AbstractDrink(String name){
        this.name = name;
    }

    public abstract DrinkTypeEnum getDrinkTypeEnum();
    
}
