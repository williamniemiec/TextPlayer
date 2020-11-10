
![](https://github.com/williamniemiec/TextPlayer/blob/master/docs/logo/logo.jpg?raw=true)

<h1 align='center'>TextPlayer</h1>

<p align='center'>Esse é um projeto feito em Java cuja funcionalidade consiste em gerar sons a partir de um texto qualquer. Ele utiliza o padrão de projeto MVC, utilizando a framework <a href="https://github.com/williamniemiec/MVC-in-Java">MVC-in-Java</a>. O principal objetivo desse projeto não é a aplicação em si, mas sim em desenvolver uma aplicação que siga boas práticas, com o uso de um código limpo e com alta legibilidade. Foram usados diversas técnicas e refatorações a fim de cumprir com esse objetivo.</p>

<hr />


## ❓ Como usar?

 1. Informar um arquivo de texto (formato `.txt`)
 2. Será aberto o player.  Escolha a opção `play` e a aplicação irá executar o som gerado através do arquivo de texto

### Teclas de atalho
|Tecla|Função
|--|--|
|`Space`|Play / pause
|`H`|Stop
|`Ctrl + N`|Abre a janela para troca de texto, pedindo para o usuário digitar um texto
|`Ctrl + O`|Abre a janela para troca de texto, pedindo para o usuário escolher um arquivo `.txt`
|`Ctrl + W`|Fecha a janela atual e volta para a tela inicial
|`Alt + F4`|Fecha a aplicação

## ✔ Requisitos 
- [JFugue 4.0.3](https://github.com/williamniemiec/TextPlayer/blob/master/lib/jfugue-4.0.3.jar) 

## ⛔ Restrições

 - O texto informado não pode ter underscores (`_`), visto que é um símbolo reservado da aplicação

## ℹ Como Funciona?
![uml](https://github.com/williamniemiec/TextPlayer/blob/master/docs/img/schema.png?raw=true)

## 🚩 Changelog
Detalhes sobre cada versão estão documentadas na [seção releases](https://github.com/williamniemiec/TextPlayer/releases).

## 🗺 Organização do projeto
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
|	KeyboardController			|	`Classe`			|	Responsável pelo comportamento do teclado	|
|	TextPlayerController 	|	`Classe`			|	Responsável pelo comportamento da [view TextPlayer](https://github.com/williamniemiec/TextPlayer/blob/master/src/views/TextPlayerView.java)		|

### models.io.input.dialog

|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	FileInputDialog 				|	`Classe`	| 	Responsável por obter 
input de arquivo através de uma janela		|
|	FileInputType|	`Enumeração`	| 	Contém os tipos de input de arquivo disponíveis		|
|	InputDialog|	`Classe abstrata`	| 	Responsável por obter um input  através de uma janela		|
|	TextInputDialog|	`Classe`	| 	Responsável por obter input de texo através de uma janela		|

### models.musical.note
|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	JFugueMusicalNote|	`Classe`	| 	Responsável por representar uma nota musical da API JFugue	|

### models.music.player
|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	JFugueMusicPlayer 				|	`Classe`	| 	Responsável por representar o player de música da API JFugue		|
|	MusicPlayer 				|	`Interface`		| 	Responsável por representar players de música		|

### models.parse
|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	JFugueMusicParser 		|	`Classe`	| 	Responsável pelo parse de um arquivo para posterior uso deste arquivo pelo JFugue		|
|	Parser 					|	`Classe`	| 	Responsável pelo parse de um arquivo. Utiliza injeção de dependência com relação a interface `ParseType`	|
|	ParseType 				|	`Interface`	| 	Todas as classes que a implementarem poderão ser utilizadas pela classe Parser para a realização do parse		|

### models.util
Contém as classes utilitárias da aplicação, sendo totalmente independentes das classes da aplicação.

### views

|        Nome        | Tipo |	Descrição	|
|----------------|-------|--------------------------------------------------|
|	HomeView 				|	`Classe`	| 	Responsável pela tela inicial da aplicação		|
|	TextPlayerView			|	`Classe`	|	Responsável pela tela do player de música da aplicação	|



## 📁 Arquivos
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
| 	resources| `Diretório`	| Contém os arquivos de configuração da aplicação, como os dicionários (resource bundles)
| 	views 				| `Diretório`	| Contém todas as classes responsáveis pela parte visual do programa
| 	Main.java 			| `Arquivo`		| Arquivo responsável pelo início da aplicação


## Imagens da aplicação

### en

#### Home
![home](https://github.com/williamniemiec/TextPlayer/blob/master/docs/app/en/home.png?raw=true)

#### MusicPlayer
![musicPlayer](https://github.com/williamniemiec/TextPlayer/blob/master/docs/app/en/player.png?raw=true)

<hr />

### pt_BR

#### Home
![home](https://github.com/williamniemiec/TextPlayer/blob/master/docs/app/pt_BR/home.png?raw=true)

#### MusicPlayer
![musicPlayer](https://github.com/williamniemiec/TextPlayer/blob/master/docs/app/pt_BR/player.png?raw=true)

## Referências
- Freeman, Eric, Elisabeth Robson, Kathy Sierra, and Bert Bates. 2004. Head First design patterns. Sebastopol, CA: O'Reilly. 
- Beck, Kant. Implementation Patterns, Addison-Wesley, 2008.
- Meyer, Bertrand. Object-Oriented Software Construction (Book/CD-ROM), Prentice Hall, 2000.
