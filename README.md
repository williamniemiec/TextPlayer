# TextPlayer
![](https://github.com/williamniemiec/TextPlayer/blob/master/media/logo/logo.jpg?raw=true)

Esse é um projeto cujo objetivo consiste em gerar sons a partir de um texto qualquer. Ele utiliza o padrão de projeto MVC, utilizando a framework [MVC-in-Java](https://github.com/williamniemiec/MVC-in-Java).

<hr />

## Requisitos

## Como usar?
...

## Como Funciona?
...

## Organização do projeto
![uml](https://github.com/williamniemiec/TextPlayer/tree/master/media/uml/uml.jpg?raw=true)

## Classes e interfaces

### core
|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|		Controller 		|	`Classe abstrata`	| 	...		|
|		Model 			|	`Interface`			| 	...		|
|		View 			|	`Interface`			| 	...		|


### controllers

|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	HomeController			|	`Classe`			|	...		|
|	TextPlayerController 	|	`Classe`			|	...		|

### models

|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	IOManager 				|	`Classe`	| 	...		|

### models.musicPlayer
|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	JFugueMusicPlayer 				|	`Classe`	| 	...		|
|	MusicPlayer 				|	`Interface`	| 	...		|

### models.parse
|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	JFugueMusicParser 				|	`Classe`	| 	...		|
|	Parser 				|	`Classe`	| 	...		|
|	ParseType 				|	`Interface`	| 	...		|

### views

|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	HomeView 				|	`Classe`	| 	...		|
|	TextPlayerView			|	`Classe`			|	...		|



## Arquivos
Dentro da pasta src encontra-se todo o conteúdo referente à aplicação.

### /
|Nome| Tipo| Descrição
|------- | --- | ----
| lib	|	 `Diretório`	| Contém as bibliotecas que a aplicação depende |
| media|	 `Diretório`	| MVC framework
| release |	 `Diretório`	| Contém arquivo executável do projeto
| src 	|	 `Diretório`	| MVC framework
| tests |	 `Diretório`	| MVC framework
| .classpath |	 `Arquivo`	| Arquivo gerado por IDE
| .project|	 `Arquivo`	| Arquivo gerado por IDE
| .classpath |	 `Arquivo`	| Arquivo gerado por IDE

### /src
|Name| Type| Function
|------- | --- | ----
| 	assets				| `Diretório`	| Contains all application content files
| 	controllers 		| `Diretório`	| Contains all application controller classes
| 	core 				| `Diretório`	| Contains the classes responsable for the MVC operations
| 	models 				| `Diretório`	| Contains all application model classes
| 	views 				| `Diretório`	| Contains all application view classes
| 	Main.java 			| `Arquivo`		| File responsible for starting the website

### /tests
|Name| Type| Function
|------- | --- | ----