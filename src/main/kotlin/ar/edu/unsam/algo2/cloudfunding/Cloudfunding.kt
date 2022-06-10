package ar.edu.unsam.algo2.cloudfunding

object Cloudfunding {
   val criteriosEleccionProyectos = mutableListOf<CriterioEleccionProyectos>()
   val proyectos = mutableListOf<Proyecto>()
   val criteriosDistribucionDinero = mutableListOf<CriterioDistribucionDinero>()

   fun obtenerProyectosElegibles(): List<CriterioEleccionProyectoDTO> =
      criteriosEleccionProyectos
         .map {
            CriterioEleccionProyectoDTO(
               criterioEleccionProyectos = it,
               proyectos = it.proyectosElegibles(proyectos)
            )
         }

}

object ModuloTransferencia {
   var cuentaOrigenId = ""
   lateinit var interfazBancariaFeucha: InterfazBancariaFeucha

   fun transferir(monto: Int, proyecto: Proyecto) {
      interfazBancariaFeucha.transferir(cuentaOrigenId, proyecto.cuentaAsociadaId, monto, 0, true, false)
   }
}

interface InterfazBancariaFeucha {
   fun transferir(cuentaOrigenId: String, cuentaDestinoId: String, montoEntero: Int, montoDecimales: Int, depositoInmediato: Boolean, deposito24hs: Boolean)
}




data class CriterioEleccionProyectoDTO(
   val criterioEleccionProyectos: CriterioEleccionProyectos,
   val proyectos: List<Proyecto>
)