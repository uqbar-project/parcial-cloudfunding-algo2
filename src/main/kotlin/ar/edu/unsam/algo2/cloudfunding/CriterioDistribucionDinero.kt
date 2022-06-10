package ar.edu.unsam.algo2.cloudfunding

import java.lang.RuntimeException

abstract class CriterioDistribucionDinero {
   fun distribuirDinero(monto: Int, proyectos: List<Proyecto>) {
      var montoADistribuir = monto
      this.validarDistribucion(monto, proyectos)
      val inversiones = mutableListOf<Inversion>()
      this.proyectosSeleccionados(proyectos).forEach { proyecto ->
         val montoProyecto = montoAInvertir(monto, proyecto, proyectos)
         inversiones.add(Inversion(montoProyecto, proyecto))
         montoADistribuir = montoADistribuir - montoProyecto
      }
      inversiones.forEach { it.distribuir() }
   }

   abstract fun montoAInvertir(monto: Int, proyecto: Proyecto, proyectos: List<Proyecto>): Int

   open fun proyectosSeleccionados(proyectos: List<Proyecto>) =
      proyectos

   fun validarDistribucion(monto: Int, proyectos: List<Proyecto>) {
      if (monto < 1000) throw UserException("Debe ingresar un monto mayor o igual a 1000")
      if (proyectos.size < 2) throw UserException("La distribución de proyectos necesita al menos 2 proyectos para funcionar")
   }
}

class PartesIguales : CriterioDistribucionDinero() {
   override fun montoAInvertir(monto: Int, proyecto: Proyecto, proyectos: List<Proyecto>) =
      monto / proyectos.size
}

class AlAzar : CriterioDistribucionDinero() {
   override fun montoAInvertir(monto: Int, proyecto: Proyecto, proyectos: List<Proyecto>) =
      if (proyectos.first() == proyecto) 500 else monto

   override fun proyectosSeleccionados(proyectos: List<Proyecto>): List<Proyecto> {
      val proyectosAlAzar = proyectos.shuffled()
      return listOf(proyectosAlAzar.first(), proyectosAlAzar.last())
   }
}

data class Inversion(val monto: Int, val proyecto: Proyecto) {
   fun distribuir() {
      proyecto.transferir(monto)
   }
}


class UserException(msg: String) : RuntimeException(msg)