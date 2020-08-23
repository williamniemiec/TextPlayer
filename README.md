# TextPlayer
![](https://github.com/williamniemiec/TextPlayer/blob/master/docs/logo/logo.jpg?raw=true)

Esse é um projeto feito em Java cujo objetivo consiste em gerar sons a partir de um texto qualquer. Ele utiliza o padrão de projeto MVC, utilizando a framework [MVC-in-Java](https://github.com/williamniemiec/MVC-in-Java).

<hr />

## Requisitos
- [JFugue 5.0.9](https://github.com/williamniemiec/TextPlayer/blob/master/lib/jfugue-5.0.9.jar) (Incluído no projeto)

## Como usar?
...

## Como Funciona?
...

## Organização do projeto
![uml](https://github.com/williamniemiec/TextPlayer/blob/master/docs/uml/uml.png?raw=true)

## Classes e interfaces

### core
|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|		Controller 		|	`Classe abstrata`	| 	Contém o frame principal da aplicação. Todas os controllers devem extender este		|
|		Model 			|	`Interface`			| 	Possuem comportamento de um [Observator](https://www.javaworld.com/article/2077258/observer-and-observable.html). Todos as classes models implementarão essa interface		|
|		View 			|	`Interface`			| 	Possuem comportamento de um [Observer](https://www.javaworld.com/article/2077258/observer-and-observable.html). Todos as classes views implementarão essa interface		|


### controllers

|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	HomeController			|	`Classe`			|	Responsável pelo comportamento da [view Home](https://github.com/williamniemiec/TextPlayer/blob/master/src/views/HomeView.java)	|
|	TextPlayerController 	|	`Classe`			|	Responsável pelo comportamento da [view TextPlayer](https://github.com/williamniemiec/TextPlayer/blob/master/src/views/TextPlayerView.java)		|

### models

|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	IOManager 				|	`Classe`	| 	Classe auxiliar para manipulação de arquivos		|

### models.musicPlayer
|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	JFugueMusicPlayer 				|	`Classe`	| 	...		|
|	MusicPlayer 				|	`Interface`		| 	...		|

### models.parse
|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	JFugueMusicParser 		|	`Classe`	| 	Responsável pelo parse de um arquivo para posterior uso deste arquivo pelo JFugue		|
|	Parser 					|	`Classe`	| 	Responsável pelo parse de um arquivo. Utiliza injeção de dependência com relação a interface `ParseType`	|
|	ParseType 				|	`Interface`	| 	Todas as classes que a implementarem poderão ser utilizadas pela classe Parser para a realização do parse		|

### views

|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	HomeView 				|	`Classe`	| 	Responsável pela tela inicial da aplicação		|
|	TextPlayerView			|	`Classe`	|	Responsável pela tela do player de música da aplicação	|



## Arquivos
Dentro da pasta src encontra-se todo o conteúdo referente à aplicação.

### /
|Nome| Tipo| Descrição
|------- | --- | ----
| bin	|	 `Diretório`	| Contém os arquivos binários da aplicação |
| dist |	 `Diretório`	| Contém os arquivos executáveis do projeto |
| docs |	 `Diretório`	| Informações relativas a documentação / Diagrama UML |
| lib	|	 `Diretório`	| Contém as bibliotecas que a aplicação depende |
| src 	|	 `Diretório`	| Contém os arquivos fonte da aplicação |
| test |	 `Diretório`	| Contém os testes da aplicação |
| .classpath |	 `Arquivo`	| Arquivo gerado por IDE |
| .project	 |	 `Arquivo`	| Arquivo gerado por IDE |

### /src
|Name| Type| Function
|------- | --- | ----
| 	assets				| `Diretório`	| Contém todos os arquivos de conteúdo da aplicação
| 	controllers 		| `Diretório`	| Contém todas as classes de controllers da aplicação
| 	core 				| `Diretório`	| Contém as classes e interfaces essenciais para o funcionamento da estrutura MVC
| 	models 				| `Diretório`	| Contém todas as classes de models da aplicação
| 	views 				| `Diretório`	| Contém todas as classes responsáveis pela parte visual do programa
| 	Main.java 			| `Arquivo`		| Arquivo responsável pelo início da aplicação

### /tests
|Name| Type| Function
|------- | --- | ----

## Imagens da aplicação

#### Home
![home](https://github.com/williamniemiec/TextPlayer/blob/master/docs/app/home.png?raw=true)

#### MusicPlayer
![musicPlayer](https://github.com/williamniemiec/TextPlayer/blob/master/docs/app/musicPlayer.png?raw=true)

## Referências
- Freeman, Eric, Elisabeth Robson, Kathy Sierra, and Bert Bates. 2004. Head First design patterns. Sebastopol, CA: O'Reilly. 
- Beck, K. Implementation Patterns, Addison-Wesley, 2008.
