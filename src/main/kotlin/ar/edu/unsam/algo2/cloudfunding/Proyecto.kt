package ar.edu.unsam.algo2.cloudfunding

import java.time.LocalDate
import java.time.temporal.ChronoUnit

enum class ORIGEN {
   NACIONAL,
   EXTRANJERO
}

abstract class Proyecto {
   var nombre: String = ""
   var descripcion: String = ""
   var dineroNecesario = 1000
   var dineroRecibido = 0
   var cuentaAsociadaId = ""
   var responsables = mutableListOf<Persona>()
   var origen = ORIGEN.NACIONAL

   fun impactoSocial() = 0.1 * dineroNecesario
   abstract fun impactoSocialEspecifico(): Int

   fun esNacional() = origen == ORIGEN.NACIONAL
   fun transferir(monto: Int) {
      dineroRecibido = dineroRecibido + monto
      ModuloTransferencia.transferir(monto, this)
   }
}

class ProyectoSocial : Proyecto() {
   var fechaInicio: LocalDate = LocalDate.now()
   override fun impactoSocialEspecifico() = 100 - this.antiguedad()
   fun antiguedad() = ChronoUnit.YEARS.between(fechaInicio, LocalDate.now()).toInt()
}

class Cooperativa : Proyecto() {
   val socios = mutableListOf<Persona>()
   override fun impactoSocialEspecifico() = socios.sumOf { it.valorPorImpactoSocial() }
}

class Ecologico : Proyecto() {
   var area: Int = 100
   override fun impactoSocialEspecifico() = 10 * area
}

data class Persona(val nombre: String, val apellidos: String, val email: String = "") {
   fun valorPorImpactoSocial() = if (tieneDobleApellido()) 45 else 30
   fun tieneDobleApellido() = apellidos.split(" ").size > 1
}

