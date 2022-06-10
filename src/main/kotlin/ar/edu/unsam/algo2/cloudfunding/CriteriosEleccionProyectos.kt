package ar.edu.unsam.algo2.cloudfunding

interface CriterioEleccionProyectos {
   fun proyectosElegibles(proyectos: List<Proyecto>): List<Proyecto>
}

class ImpactoSocial : CriterioEleccionProyectos {
   override fun proyectosElegibles(proyectos: List<Proyecto>): List<Proyecto> =
      proyectos.sortedByDescending { it.impactoSocial() }.take(3)
}

class MasYMenosDinero : CriterioEleccionProyectos {
   override fun proyectosElegibles(proyectos: List<Proyecto>): List<Proyecto> {
      val proyectosPorNecesidadDeDinero = proyectos.sortedBy { it.dineroNecesario }
      return listOf(proyectosPorNecesidadDeDinero.first(), proyectosPorNecesidadDeDinero.last())
   }
}

class Nacional : CriterioEleccionProyectos {
   override fun proyectosElegibles(proyectos: List<Proyecto>) =
      proyectos.filter { it.esNacional() }
}

class Combinatoria : CriterioEleccionProyectos {
   val criteriosEleccionProyectos = mutableListOf<CriterioEleccionProyectos>()

   override fun proyectosElegibles(proyectos: List<Proyecto>) =
      proyectos.filter { proyecto -> cumpleAlgunoDeLosCriterios(proyecto, proyectos) }

   private fun cumpleAlgunoDeLosCriterios(proyecto: Proyecto, proyectos: List<Proyecto>) =
      criteriosEleccionProyectos
         .any { criterio -> criterio.proyectosElegibles(proyectos).contains(proyecto) }

}