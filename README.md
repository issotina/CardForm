#ANDROID CARD FORM
**A better Android credit/debit card interactive form without writing a lot of code**

CardForm can be used as a simple credit card validation form with any credit card form and any payment API (without you changing anything). Everything is created with Android SDK - no picture is required.

![Demo App]
(https://raw.githubusercontent.com/geeckmc/CardForm/master/demo.gif)

##Installation
**by Gradle:**
 ```gradle
 compile 'com.craftman.cardform:cardform:0.0.2'
```

##Setup and usage

**Use CardForm layout in your xml code:**
```xml
<com.craftman.cardform.CardForm
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/card_form"/>`
 ```
 **In your java code**
 ```java
CardForm cardForm = (CardForm) findViewById(R.id.card_form);
cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
    @Override
    public void onClick(Card card) {
        //Your code here!! use card.getXXX() for get any card property
        //for instance card.getName();
    }
});
```



#License

```
Copyright 2016 Shadaï ALI (craftman).

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
   
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
