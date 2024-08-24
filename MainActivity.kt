package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication1.databinding.ActivityMainBinding
import android.media.MediaPlayer

class MainActivity : AppCompatActivity() {

//adiconando variavéis de som
    private lateinit var wrongGuessSound: MediaPlayer
    private lateinit var rightGuessSound: MediaPlayer
    private lateinit var finishGuessSound: MediaPlayer
    private lateinit var loserGuessSound: MediaPlayer


//adicionado as outras variáveis
    private lateinit var binding: ActivityMainBinding
    private var word = listOf("comida","carro","desenho")
    private var palavraselecionada= word.random().uppercase()
    private var palavraoculta = CharArray(palavraselecionada.length) {'_'}
    private var Tentativas= 6
    private var guessedletters = mutableListOf<Char>()

    //liberando recursos através do onDestroy
    override fun onDestroy() {
        super.onDestroy()

        wrongGuessSound.release()
        rightGuessSound.release()
        finishGuessSound.release()
        loserGuessSound.release()


    }
//iniciando o código
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//iniciando as variáveis de som e vinculando a elas um som que está no raw
        wrongGuessSound = MediaPlayer.create(this, R.raw.beep)
        rightGuessSound = MediaPlayer.create(this, R.raw.correct)
        finishGuessSound = MediaPlayer.create(this, R.raw.game)
        loserGuessSound = MediaPlayer.create(this, R.raw.gameover)

//iniciando variavel para recarregar jogo e associanso ao botão
binding.buttonrei.setOnClickListener {
    reload()
}


        atualizar()
//funcao para a entrada de dados
        //o editEntrada tá no layout

        binding.buttonAdivinhar.setOnClickListener {


            val entrada = binding.editEntrada.text.toString().uppercase()
            if (entrada.isNotEmpty()) {
                val word =entrada[0]
                if (!guessedletters.contains((word))){
                    guessedletters.add(word)
                    if(palavraselecionada.contains(word)){
                        for(i in palavraselecionada.indices){
                            if (palavraselecionada[i] == word){
                                palavraoculta[i] = word
                                rightGuessSound.start()
                            }
                        }
                    }else{
                        Tentativas--
                        //a função de som é utilizada
                        wrongGuessSound.start()

                    }
                }
                binding.editEntrada.text.clear()
                atualizar()
            }
        }

    }
    //comando para criar a funcao que recarrega a tela
    private fun atualizar() {
        binding.textOculto.text = palavraoculta.joinToString (" ")
        binding.textTentativas.text="restam:$Tentativas"
        if (!palavraoculta.contains('_')){
            binding.textStatus.text="parabéns $palavraselecionada"
            binding.buttonAdivinhar.isEnabled=false
            binding.buttonrei.isEnabled=true
            finishGuessSound.start()


        }else if (Tentativas<=0){
            binding.textStatus.text="Perdeu!era ;$palavraselecionada"
            binding.buttonAdivinhar.isEnabled=false
            binding.buttonrei.isEnabled=true
            loserGuessSound.start()


        }
        //para atualizar a imagem do boneco
        atualizarimg()

    }
//funçao para atualizar a imagem do boneco
 private fun atualizarimg(){
     val forca = when(Tentativas){

         6-> R.drawable.vazia1
         5-> R.drawable.cabeca
         4-> R.drawable.corpo
         3-> R.drawable.braco1
         2-> R.drawable.braco2
         1->R.drawable.perna1
         0->R.drawable.inteiro

         else -> {
             R.drawable.cabeca
         }
     }
     binding.boneco.setImageResource(forca)
 }

//funcao para ativar o botao recarregar
         private fun reload() {
novojogo()
binding.buttonAdivinhar.isEnabled=true
        binding.buttonrei.isEnabled=false
    }
    //essa funcao é usada para criar uma nova rodada e vau ser aplicada no reload
private fun novojogo(){

     word = listOf("kotlin","android","desenvolvedor")
     palavraselecionada= word.random().uppercase()
    palavraoculta = CharArray(palavraselecionada.length) {'_'}
     Tentativas= 6
    guessedletters = mutableListOf<Char>()
    atualizar()
}
}