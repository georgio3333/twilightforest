package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.block.TFBlocks;

public class EntityTFMoonwormShot extends EntityTFThrowable {

	public EntityTFMoonwormShot(EntityType<? extends EntityTFMoonwormShot> type, World world) {
		super(type, world);
	}

	public EntityTFMoonwormShot(EntityType<? extends EntityTFMoonwormShot> type, World world, LivingEntity thrower) {
		super(type, world, thrower);
		shoot(thrower, thrower.rotationPitch, thrower.rotationYaw, 0F, 1.5F, 1.0F);
	}

	@Override
	public void tick() {
		super.tick();
		makeTrail();
	}

	@Override
	public float getBrightness() {
		return 1.0F;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getBrightnessForRender() {
		return 15728880;
	}


	private void makeTrail() {
//		for (int i = 0; i < 5; i++) {
//			double dx = posX + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
//			double dy = posY + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
//			double dz = posZ + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
//			
//			double s1 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.17F;
//			double s2 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.80F;
//			double s3 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.69F;
//
//			world.spawnParticle("mobSpell", dx, dy, dz, s1, s2, s3);
//		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public float getCollisionBorderSize() {
		return 1.0F;
	}

	@Override
	protected float getGravityVelocity() {
		return 0.03F;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			int stateId = Block.getStateId(TFBlocks.moonworm.getDefaultState());
			for (int i = 0; i < 8; ++i) {
				this.world.addParticle(ParticleTypes.BLOCK_CRACK, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, stateId);
			}
		} else {
			super.handleStatusUpdate(id);
		}
	}

	@Override
	protected void onImpact(RayTraceResult ray) {
		if (!world.isRemote) {
			if (ray.typeOfHit == Type.BLOCK) {

				BlockPos pos = ray.getBlockPos().offset(ray.sideHit);
				BlockState currentState = world.getBlockState(pos);

				if (currentState.getBlock().isReplaceable(world, pos)) {
					world.setBlockState(pos, TFBlocks.moonworm.getDefaultState().with(BlockDirectional.FACING, ray.sideHit));
					// todo sound
				}
			}

			if (ray.entityHit != null) {
				ray.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
			}

			this.world.setEntityState(this, (byte) 3);
			this.setDead();
		}
	}
}
