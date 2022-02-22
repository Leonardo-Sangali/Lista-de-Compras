package com.example.listadecompras

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() { //217

    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)


        //definição do ouvinte do botão
        btn_inserir.setOnClickListener {

            //pegando os valores digitados pelo usuário
            val produto = txt_produto.text.toString()
            val qtd = txt_qtd.text.toString()
            val valor = txt_valor.text.toString()

            //verificando se o usuário digitou algum valor
            if (produto.isNotEmpty() && qtd.isNotEmpty() && valor.isNotEmpty()) {

                val prod = Produto(produto, qtd.toInt(), valor.toDouble(), imageBitMap)
                produtosGlobal.add(prod)

                txt_produto.text.clear()
                txt_qtd.text.clear()
                txt_valor.text.clear()

            } else {
                txt_produto.error =
                    if (txt_produto.text.isEmpty()) "Preenchao nome do produto" else null
                txt_qtd.error = if (txt_qtd.text.isEmpty()) "Preencha a quantidade" else null
                txt_valor.error = if (txt_valor.text.isEmpty()) "Preencha o valor" else null
            }

            fun abrirGaleria() {
                //definindo a ação de conteúdo
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                //definindo filtro para imagens
                intent.type = "image/*"
                //inicializando a activity com resultado startActivityForResult(Intent.createChooser(intent, "Selecion e uma imagem"), COD_IMAGE)
            }

            img_foto_produto.setOnClickListener {
                abrirGaleria()
            }

        }

    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK
        ) {

            if (data != null) {

                //lendo a uri com a imagem
                val inputStream = data.getData()?.let { contentResolver.openInputStream(it) };

                //transformando o resultado em bitmap
                imageBitMap = BitmapFactory.decodeStream(inputStream)

                //Exibir a imagem no aplicativo
                img_foto_produto.setImageBitmap(imageBitMap)

            }

        }
    }
}


