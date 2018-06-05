```sql
create  TABLE  user  (  
id  INT  auto_increment  NOT  NULL,  
username  VARCHAR(30)  NOT  NULL,  
contraseña  VARCHAR(65)  NOT  NULL,  
bio  VARCHAR(700),  
picture  VARCHAR(300)  DEFAULT  'https://cdn4.iconfinder.com/data/icons/eldorado-user/40/user-512.png',  
banner  VARCHAR(500),  
likes  INT,  
usefull  INT,  
dislikes  INT,  
CONSTRAINT  user_pk  PRIMARY  KEY  (id),  
CONSTRAINT  user_unique  UNIQUE  (username)  
);
CREATE  TABLE  pregunta (  
id  INT  NOT  NULL  auto_increment,  
texto  VARCHAR(700)  NOT  NULL,  
likes INT  DEFAULT  0,  
useful  INT  DEFAULT  0,  
dislike  INT  DEFAULT  0,  
categoria  INT  NOT  NULL  ,  
userpreg  INT  NULL,  
useranws  INT  NOT  NULL,  
PRIMARY  KEY  (id),  
CONSTRAINT  userpregunta_fk  FOREIGN  KEY  (userpreg)  REFERENCES  user(id),  
CONSTRAINT  userrecibepregunta_fk  FOREIGN  KEY  (useranws)  REFERENCES  user(id)  
);
CREATE  TABLE  respuesta  (  
id  INT  NOT  NULL  auto_increment,  
texto VARCHAR(500)  NOT  NULL,  
likes  INT  DEFAULT  0,  
useful INT  DEFAULT  0,  
dislikes INT  DEFAULT  0,  
pregunta_id INT  NOT  NULL,  
PRIMARY  KEY  (id),  
CONSTRAINT  fk_respuesta_a_pregunta  FOREIGN  KEY  (pregunta_id)  REFERENCES  pregunta(id)  
)  
CREATE TABLE categoria(
id int NOT NULL auto_increment,
Nombre varchar(30) NOT NULL
PRIMARY KEY (id)
)

CREATE  TABLE  categoria_user  (  
usuario  INT  NOT  NULL,  
categoria  INT  NOT  NULL,  
PRIMARY  KEY  (usuario,  categoria),  
CONSTRAINT  cat_to_user_fk  FOREIGN  KEY  (usuario)  REFERENCES  user(id),  
CONSTRAINT  cat_to_rcat_fk  FOREIGN  KEY  (categoria)  REFERENCES  categoria(id)  
)  
CREATE TABLE usuario_vota_pregunta(
usuario INT NOT NULL,
pregunta INT NOT NULL
PRIMARY KEY(usuario,pregunta)
)
--- un Insert de un Usuario (Nester) para que hagais pruebas 
insert  INTO  user  (  username,  contraseña,  bio)  VALUES  (  
"Nester",  
sha("Patata"),  
"Hello, im Pepito",  
)  

-- Un Alter table que añade una columna a categorias con un valor por defecto
ALTER  TABLE  categoria  ADD  imagen  VARCHAR(500)  DEFAULT  "https://i.redd.it/ounq1mw5kdxy.gif";  
  
--- update
update `user`  
SET  banner  =  "https://preview.ibb.co/mw4Rao/Banner.png"  
WHERE  username  =  "Raxtran";
```
