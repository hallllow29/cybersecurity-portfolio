-- Calcular perda material no ConsumoMaterialReal
CREATE TRIGGER tr_ConsumoMaterialReal_CalcularPerda
ON ConsumoMaterialReal
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE cmr
    SET percentagemPerdaMaterial =
        CASE
            WHEN eo.quantidadeEnviada IS NULL OR eo.quantidadeEnviada = 0 THEN NULL
            WHEN cmr.quantidadeUtilizada IS NULL THEN NULL
            ELSE ROUND(
                100.0 * (cmr.quantidadeUtilizada - (cmr.quantidadePlaneada * eo.quantidadeEnviada))
                / NULLIF(cmr.quantidadePlaneada * eo.quantidadeEnviada, 0),
                2
            )
        END
    FROM ConsumoMaterialReal cmr
    JOIN inserted i ON i.consumoID = cmr.consumoID
    JOIN ExecucaoOperacao eo ON cmr.execucaoID = eo.execucaoID
    WHERE cmr.quantidadeUtilizada IS NOT NULL;
END;