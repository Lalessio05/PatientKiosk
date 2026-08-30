package com.lalessio.patientkiosk.ui.result

import com.lalessio.patientkiosk.domain.Band

/**Fasce di comodo per le @Preview delle schermate di risultato: non usate a runtime.**/
internal object PreviewBands {
    val WHO5 = listOf(
        Band(0, 28, "Benessere molto basso", "Valore compatibile con un disagio significativo."),
        Band(29, 50, "Benessere ridotto", "Approfondire con il medico."),
        Band(51, 100, "Benessere adeguato", "Nessun elemento di attenzione."),
    )

    val HADS = listOf(
        Band(0, 7, "Nella norma", "Nessun elemento di attenzione."),
        Band(8, 10, "Borderline", "Da rivalutare nel tempo."),
        Band(11, 21, "Caso probabile", "Approfondire con il medico."),
    )
}
