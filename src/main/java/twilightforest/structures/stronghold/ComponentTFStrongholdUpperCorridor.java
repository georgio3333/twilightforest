package twilightforest.structures.stronghold;

import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.TFFeature;

import java.util.List;
import java.util.Random;

public class ComponentTFStrongholdUpperCorridor extends StructureTFStrongholdComponent {

	public ComponentTFStrongholdUpperCorridor() {
	}

	public ComponentTFStrongholdUpperCorridor(TFFeature feature, int i, Direction facing, int x, int y, int z) {
		super(feature, i, facing, x, y, z);
	}

	@Override
	public MutableBoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
		return MutableBoundingBox.getComponentToAddBoundingBox(x, y, z, -2, -1, 0, 5, 5, 9, facing);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random random) {
		super.buildComponent(parent, list, random);

		// make a random component at the end
		addNewUpperComponent(parent, list, random, Rotation.NONE, 2, 1, 9);

	}

	@Override
	public boolean addComponentParts(IWorld world, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		if (this.isLiquidInStructureBoundingBox(world, sbb)) {
			return false;
		} else {
			placeUpperStrongholdWalls(world.getWorld(), sbb, 0, 0, 0, 4, 4, 8, rand, deco.randomBlocks);

			// entrance doorway
			placeSmallDoorwayAt(world.getWorld(), rand, 2, 2, 1, 0, sbb);

			// end
			placeSmallDoorwayAt(world.getWorld(), rand, 2, 2, 1, 8, sbb);

			return true;
		}
	}

	@Override
	public boolean isComponentProtected() {
		return false;
	}
}
